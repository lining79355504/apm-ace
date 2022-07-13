package com.dianping.cat.build;

import java.util.ArrayList;
import java.util.List;

import com.dianping.cat.message.spi.codec.NativeMessageCodec;
import org.unidal.lookup.configuration.AbstractResourceConfigurator;
import org.unidal.lookup.configuration.Component;

import com.dianping.cat.message.spi.MessageCodec;
import com.dianping.cat.message.spi.codec.BufferWriter;
import com.dianping.cat.message.spi.codec.EscapingBufferWriter;
import com.dianping.cat.message.spi.codec.PlainTextMessageCodec;

class CodecComponentConfigurator extends AbstractResourceConfigurator {
	@Override
	public List<Component> defineComponents() {
		List<Component> all = new ArrayList<Component>();

		all.add(C(BufferWriter.class, EscapingBufferWriter.ID, EscapingBufferWriter.class));

		all.add(C(MessageCodec.class, NativeMessageCodec.ID, NativeMessageCodec.class)); //
//		      .req(BufferWriter.class, EscapingBufferWriter.ID));

		all.add(C(MessageCodec.class, PlainTextMessageCodec.ID, PlainTextMessageCodec.class)); //
//				.req(BufferWriter.class, EscapingBufferWriter.ID));

		return all;
	}
}
