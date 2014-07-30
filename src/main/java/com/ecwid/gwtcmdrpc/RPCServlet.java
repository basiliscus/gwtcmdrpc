package com.ecwid.gwtcmdrpc;

import com.google.gwt.core.shared.GwtIncompatible;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
@GwtIncompatible
public abstract class RPCServlet extends RemoteServiceServlet implements RPC.Service {
	private static final Logger log = Logger.getLogger(RPCServlet.class.getName());

	public RPCServlet() { }

	@Override
    public final <T extends Response> T call(Request<T> request) throws ServiceException {
		long start = System.nanoTime();
		int identity = System.identityHashCode(request);

		if (log.isLoggable(Level.INFO)) {
			StringBuilder sb = new StringBuilder("Request execution started");
			sb.append("\n").append("Request: ").append(request);
			sb.append("\n").append("Request identity: ").append(identity);
			log.log(Level.INFO, sb.toString());
		}

		try {
			T response = execute(request);

			if (log.isLoggable(Level.INFO)) {
				StringBuilder sb = new StringBuilder("Request execution complete");
				sb.append("\n").append("Request identity: ").append(identity);
				sb.append("\n").append("Duration: ").append(System.nanoTime() - start).append(" nanos");
				sb.append("\n").append("Response: ").append(response);
				log.log(Level.INFO, sb.toString());
			}

			return response;
		} catch (Throwable exception) {
			if (log.isLoggable(Level.INFO)) {
				StringBuilder sb = new StringBuilder("Request execution failed");
				sb.append("\n").append("Request identity: ").append(identity);
				sb.append("\n").append("Duration: ").append(System.nanoTime() - start).append(" nanos");
				log.log(Level.INFO, sb.toString(), exception);
			}

			throw exception;
		}
    }

	protected abstract <T extends Response> T execute(Request<T> request) throws ServiceException;
}