import fetchIntercept from 'fetch-intercept';

export class FetchInterceptor {
    static theInstance = null;
    session;
    router;
    unregister;
    constructor(session, router) {
        this.session = session;
        this.router = router;

        FetchInterceptor.theInstance = this;
        this.unregister = fetchIntercept.register(this)
    }

    request(url, options){
        let token = FetchInterceptor.theInstance.session.getTokenFromBrowserStorage();

        if (token === null) {
            return [url, options];
        } else if (options === null) {
            return [url, { headers: { 'Authorization': token } }];
        } else {
            let newOptions = { ...options };
            newOptions.headers = {
                ...newOptions.headers,
                'Authorization': token
            }
            return [url, newOptions];
        }
    }
    // requestError(error){}
    // response(response){}
    // responseError(error){}
}