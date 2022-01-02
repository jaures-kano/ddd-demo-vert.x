package com.cmc.y3group.ddd.infrastructure.system.vertx.eventbus;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class GenericCodec<T> implements MessageCodec<T, T> {
	private final Class<T> cls;

	/**
	 * @param cls Class.
	 */
	public GenericCodec(Class<T> cls) {
		super();
		this.cls = cls;
	}

	/** {@inheritDoc} */
	@Override
	public void encodeToWire(Buffer buf, T s) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(s);
			out.flush();
			byte[] yourBytes = bos.toByteArray();
			buf.appendInt(yourBytes.length);
			buf.appendBytes(yourBytes);
			out.close();
		} catch (IOException ignored) {
		} finally {
			try {
				bos.close();
			} catch (IOException ignored) {
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public T decodeFromWire(int pos, Buffer buf) {
		// My custom message starting from this *position* of buffer
		int _pos = pos;

		// Length of JSON
		int length = buf.getInt(_pos);

		// Jump 4 because getInt() == 4 bytes
		byte[] yourBytes = buf.getBytes(_pos += 4, _pos += length);
		ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
		try {
			ObjectInputStream ois = new ObjectInputStream(bis);
			@SuppressWarnings("unchecked")
			T msg = (T) ois.readObject();
			ois.close();
			return msg;
		} catch (IOException | ClassNotFoundException e) {
			log.error("Listen failed " + e.getMessage(), e);
		} finally {
			try {
				bis.close();
			} catch (IOException ignored) {
			}
		}
		return null;
	}

	@Override
	public T transform(T customMsg) {
		return customMsg;
	}

	@Override
	public String name() {
		return cls.getSimpleName() + "Codec";
	}

	@Override
	public byte systemCodecID() {
		// Always -1
		return -1;
	}
}