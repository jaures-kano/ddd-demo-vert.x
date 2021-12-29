package com.cmc.y3group.ddd.infrastructure.support;

public final class Function {
	@FunctionalInterface
	public interface BitFunction<I1, O> {
		O apply(I1 t);
	}

	@FunctionalInterface
	public interface TupleBitFunction<I1, I2, O> {
		O apply(I1 i1, I2 i2);
	}
}
