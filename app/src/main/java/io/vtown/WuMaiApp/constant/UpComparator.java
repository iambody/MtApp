package io.vtown.WuMaiApp.constant;

import java.util.Comparator;

import io.vtown.WuMaiApp.module.sign.Bsign;

/**
 * @author 作者 大兔兔 wangyongkui@v-town.cc
 * @version 创建时间：2016-4-26 下午5:45:21
 * 入参进行签名所有参数根据key正序排序
 */
public class UpComparator implements Comparator<Bsign> {
	@Override
	public int compare(Bsign lhs, Bsign rhs) {
		if (lhs.getId().compareTo(rhs.getId()) < 0)
			return -1;
		else
			return 1;
	}

}
