/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec.sctp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.MessageList;
import io.netty.channel.sctp.SctpMessage;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * A ChannelHandler which transform {@link ByteBuf} to {@link SctpMessage}  and send it through a specific stream
 * with given protocol identifier.
 *
 */
public class SctpOutboundByteStreamHandler extends MessageToMessageEncoder<ByteBuf> {
    private final int streamIdentifier;
    private final int protocolIdentifier;

    /**
     * @param streamIdentifier      stream number, this should be >=0 or <= max stream number of the association.
     * @param protocolIdentifier    supported application protocol id.
     */
    public SctpOutboundByteStreamHandler(int streamIdentifier, int protocolIdentifier) {
        this.streamIdentifier = streamIdentifier;
        this.protocolIdentifier = protocolIdentifier;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, MessageList<Object> out) throws Exception {
        out.add(new SctpMessage(streamIdentifier, protocolIdentifier, msg.retain()));
    }
}
