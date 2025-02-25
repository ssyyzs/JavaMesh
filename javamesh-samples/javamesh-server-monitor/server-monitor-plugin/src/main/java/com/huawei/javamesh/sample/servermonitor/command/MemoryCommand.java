/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021-2021. All rights reserved.
 */

package com.huawei.javamesh.sample.servermonitor.command;

import com.huawei.javamesh.sample.servermonitor.common.Constant;

import java.io.InputStream;
import java.util.List;

/**
 * 执行指令：cat /proc/meminfo
 */
public class MemoryCommand extends CommonMonitorCommand<MemoryCommand.MemInfo> {

    private static final String COMMAND = "cat /proc/meminfo";

    private static final String MEMORY_TOTAL = "MemTotal";
    private static final String MEMORY_FREE = "MemFree";
    private static final String SWAP_CACHED = "SwapCached";
    private static final String BUFFERS = "Buffers";
    private static final String CACHED = "Cached";

    @Override
    public String getCommand() {
        return COMMAND;
    }

    /**
     * 原泛PaaS类：com.huawei.apm.plugin.collection.util.MemoryParser parse方法
     */
    @Override
    public MemInfo parseResult(InputStream inputStream) {
        final List<String> lines = readLines(inputStream);

        long memoryTotal = -1L;
        long memoryFree = -1L;
        long buffers = -1L;
        long cached = -1L;
        long swapCached = -1L;

        for (String line : lines) {
            String[] memInfo = line.split(Constant.REGEX_MULTI_SPACES);
            final String category = memInfo[0];
            final long value = Long.parseLong(memInfo[1]);
            if (category.startsWith(MEMORY_TOTAL)) {
                memoryTotal = value;
            } else if (category.startsWith(MEMORY_FREE)) {
                memoryFree = value;
            } else if (category.startsWith(BUFFERS)) {
                buffers = value;
            } else if (category.startsWith(CACHED)) {
                cached = value;
            } else if (category.startsWith(SWAP_CACHED)) {
                swapCached = value;
            }
        }

        if (memoryTotal < 0 || memoryFree < 0 || buffers < 0 || cached < 0 || swapCached < 0) {
            return null;
        }
        return new MemInfo(memoryTotal, memoryFree, buffers, cached, swapCached);
    }


    public static class MemInfo {
        private final long memoryTotal;
        private final long memoryFree;
        private final long buffers;
        private final long cached;
        private final long swapCached;

        public MemInfo(long memoryTotal, long memoryFree, long buffers, long cached, long swapCached) {
            this.memoryTotal = memoryTotal;
            this.memoryFree = memoryFree;
            this.buffers = buffers;
            this.cached = cached;
            this.swapCached = swapCached;
        }

        public long getMemoryTotal() {
            return memoryTotal;
        }

        public long getMemoryFree() {
            return memoryFree;
        }

        public long getBuffers() {
            return buffers;
        }

        public long getCached() {
            return cached;
        }

        public long getSwapCached() {
            return swapCached;
        }
    }
}
