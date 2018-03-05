package com.kivi.framework.web.shiro.util;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Base64Utils;

import com.kivi.framework.properties.KtfProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

	private final KtfProperties ktfProperties;

	private static String signKey = "";

	public JwtUtil(KtfProperties ktfProperties) {
		this.ktfProperties = ktfProperties;

		signKey = this.generalKey();
	}

	/**
	 * 由字符串生成加密key
	 * 
	 * @return
	 */
	public String generalKey() {
		SecretKey key = new SecretKeySpec(ktfProperties.getShiro().getToken().getJwt().getSecretSeed().getBytes(),
				"AES");

		return Base64Utils.encodeToString(key.getEncoded());
	}

	/**
	 * 创建jwt
	 * 
	 * @param id
	 * @param subject
	 * @param ttlMillis
	 * @return
	 * @throws Exception
	 */
	public String createJWT(String id, String subject) throws Exception {
		SignatureAlgorithm signatureAlgorithm = ktfProperties.getShiro().getToken().getJwt().getSignAlg();
		long ttlMillis = ktfProperties.getShiro().getToken().getTtl();
		long nowMillis = System.currentTimeMillis();
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
				.setHeaderParam("alg", signatureAlgorithm.getValue())
				.setIssuer(ktfProperties.getShiro().getToken().getJwt().getIssuer()).setIssuedAt(new Date(nowMillis))
				.setSubject(subject).signWith(signatureAlgorithm, signKey);

		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}

	/**
	 * 解密jwt
	 * 
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public Claims parseJWT(String jwt) throws Exception {
		Claims claims = Jwts.parser().setSigningKey(signKey).parseClaimsJws(jwt).getBody();
		return claims;
	}

	public static void main(String[] args) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		String stringKey = "hmackey";
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256").setIssuer("joe")
				.signWith(signatureAlgorithm, Base64Utils.encodeToString(stringKey.getBytes()));
		System.out.println(builder.compact());

		KtfProperties ktfProperties = new KtfProperties();
		JwtUtil jwt = new JwtUtil(ktfProperties);
		try {
			Claims claims = jwt.parseJWT(
					"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJraXZpIiwiaWF0IjoxNTEwNzI1NzU5LCJzdWIiOiJ1c2VyMDAxIn0.vEEHacZTy-0rZw28d0viXP0q5a92B9v7c91tI6jb1_8");
			System.out.println(claims.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
