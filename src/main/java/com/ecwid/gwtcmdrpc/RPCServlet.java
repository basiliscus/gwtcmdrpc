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
    public <T extends Response> T call(Request<T> request) throws ServiceException {
		log.log(Level.INFO, "Request execution started: " + request);
		try {
			T response = getRequestExecutor(request).execute(request);
			log.log(Level.INFO, "Request execution complete: " + request + "\nResponse: "+response);
			return response;
		} catch (Throwable exception) {
			log.log(Level.INFO, "Request execution failed: " + request, exception);
			throw exception;
		}
    }

	@SuppressWarnings("unchecked")
	private <T extends Response> RequestExecutor<Request<T>, T> getRequestExecutor(Request<T> request) {
		ExecutedBy annotation = request.getClass().getAnnotation(ExecutedBy.class);
		if (annotation == null) {
			throw new IllegalArgumentException(request.getClass() + " must be annotated with " + ExecutedBy.class);
		}

		return (RequestExecutor) getExecutorInstance(annotation.value());
	}

	protected abstract <E extends RequestExecutor<?, ?>> E getExecutorInstance(Class<E> clazz);
}