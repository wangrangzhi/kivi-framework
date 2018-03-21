package com.kivi.framework.db.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.kivi.framework.properties.KtfProperties;
import com.kivi.framework.util.kit.StrKit;

@Configuration
@EnableTransactionManagement
public class TransactionConfiguration {
    private static final int TX_METHOD_TIMEOUT = -1;

    /* 事务拦截类型 */
    @Bean( "txSource" )
    public TransactionAttributeSource transactionAttributeSource( KtfProperties ktfProperties ) {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        Map<String, TransactionAttribute> txMap = new HashMap<>();

        if (StrKit.isNotEmpty(ktfProperties.getCommon().getTxAdviceNotSupported())) {
            /* 只读事务，不做更新操作 */
            RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
            readOnlyTx.setReadOnly(true);
            readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

            String[] methods = StrKit.split(ktfProperties.getCommon().getTxAdviceNotSupported(), StrKit.COMMA);
            for (String method : methods)
                txMap.put(method, readOnlyTx);
        }

        if (StrKit.isNotEmpty(ktfProperties.getCommon().getTxAdviceRequired())) {
            /* 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 */
            RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute(
                    TransactionDefinition.PROPAGATION_REQUIRED,
                    Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
            requiredTx.setTimeout(TX_METHOD_TIMEOUT);

            String[] methods = StrKit.split(ktfProperties.getCommon().getTxAdviceRequired(), StrKit.COMMA);
            for (String method : methods)
                txMap.put(method, requiredTx);
        }

        if (StrKit.isNotEmpty(ktfProperties.getCommon().getTxAdviceSupports())) {
            /* 如果当前没有事务存在，就以非事务方式执行；如果有，就使用当前事务 */
            RuleBasedTransactionAttribute supportsTx = new RuleBasedTransactionAttribute();
            supportsTx.setReadOnly(true);
            supportsTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);

            String[] methods = StrKit.split(ktfProperties.getCommon().getTxAdviceSupports(), StrKit.COMMA);
            for (String method : methods)
                txMap.put(method, supportsTx);
        }

        source.setNameMap(txMap);

        return source;
    }

    /* 事务拦截器 */
    @Bean( "txInterceptor" )
    TransactionInterceptor getTransactionInterceptor( PlatformTransactionManager tx, KtfProperties ktfProperties ) {
        return new TransactionInterceptor(tx, transactionAttributeSource(ktfProperties));
    }

    /** 切面拦截规则 参数会自动从容器中注入 */
    @Bean
    public AspectJExpressionPointcutAdvisor pointcutAdvisor( TransactionInterceptor txInterceptor,
            KtfProperties ktfProperties ) {
        AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        pointcutAdvisor.setAdvice(txInterceptor);
        pointcutAdvisor.setExpression(ktfProperties.getCommon().getTxPointcutExpression());
        return pointcutAdvisor;
    }
}
