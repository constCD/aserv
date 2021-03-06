/**
 * Copyright 2014 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2014年8月27日 下午1:58:10
 */
package com.absir.aserv.system.service;

import com.absir.core.kernel.KernelLang.CallbackTemplate;

import java.io.Serializable;

public interface IMergeService {

    public Object merge(CallbackTemplate<Object> merge, Serializable id);

}
