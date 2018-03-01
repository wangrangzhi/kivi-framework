package com.kivi.framework.des;

import com.kivi.framework.des.DESHelper;

public class DESHelperTest {

	public static void main(String[] args) {
		String seed1 = "AAAAAAAAAAAAAuth";
		String seed2 = "BBBBBBBBBBBBBAuth";
		System.out.println("key:" + DESHelper.hexEncode(DESHelper.generate3DesKey(seed1, seed2)));
		System.out.println("dis:"
				+ DESHelper.hexEncode(DESHelper.disperse3desKey(seed1 + seed2, "112233445566778800000000".getBytes())));

	}

}
