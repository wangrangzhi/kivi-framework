/**
 * 
 */
package com.kivi.framework.web.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Eric
 *
 */

@Setter
@Getter
public class RoleResourceVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long roleId;

	private String resourcesId;

}
