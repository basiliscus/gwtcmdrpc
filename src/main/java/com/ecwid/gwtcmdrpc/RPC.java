package com.ecwid.gwtcmdrpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class RPC {
    private static final Logger log = Logger.getLogger(RPC.class.getName());

	private final ServiceAsync serviceAsync;

    public RPC(ServiceAsync serviceAsync) {
        this.serviceAsync = serviceAsync;
    }

	public final <T extends Response> void call(Request<T> request, ResponseConsumer<T> responseConsumer) {
		call(request, responseConsumer, new FailureConsumer() {
			@Override
			public void accept(Throwable caught) { }
		});
	}

    public <T extends Response> void call(final Request<T> request, final ResponseConsumer<T> responseConsumer, final FailureConsumer failureConsumer) {
		log.log(Level.INFO, "RPC request started: " + request);
        serviceAsync.call(request, new AsyncCallback<T>() {
            @Override
            public void onSuccess(T response) {
				log.log(Level.INFO, "RPC request complete: " + request + "\nResponse: " + response);
                responseConsumer.accept(response);
            }

            @Override
            public void onFailure(Throwable caught) {
                log.log(Level.INFO, "RPC request failed: " + request, caught);
                failureConsumer.accept(caught);
            }
        });
    }

	@RemoteServiceRelativePath(".." + Service.SERVICE_PATH)
	public static interface Service extends RemoteService {
		public static final String SERVICE_PATH = "/cmd-rpc";

		public <T extends Response> T call(Request<T> request) throws ServiceException;
	}

	public static interface ServiceAsync {
		public <T extends Response> void call(Request<T> request, AsyncCallback<T> callback);
	}
}
