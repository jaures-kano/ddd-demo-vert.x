package com.cmc.y3group.ddd.infrastructure.system.vertx.http.router.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VGetMapping {
	String name() default "";
	String path() default "";
}
