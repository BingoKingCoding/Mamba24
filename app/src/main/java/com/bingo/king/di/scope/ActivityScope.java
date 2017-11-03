package com.bingo.king.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/3 10:39.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface ActivityScope
{
}
