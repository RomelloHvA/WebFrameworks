import fetchIntercept from 'fetch-intercept';

/**
 * This class intercepts all fetch requests and responses.
 * It adds the Authorization header to all requests.
 * It redirects to the sign-in page if the response status is 401.
 * @param {SessionSbService} session
 * @param {Router} router 
 * @param {unregister} unregister
 * @returns {FetchInterceptor} the instance of the class
 * 
 */

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

    /**
     * All fetch requests are intercepted by this method.
     * It adds the Authorization header to all requests.
     * @param {String} url the url of the request
     * @param {Object} options  the options of the request
     * @returns the url and the options of the request 
     */
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
    /**
     * This method intercepts all fetch responses.
     * It redirects to the sign-in page if the response status is 401.
     * @param {*} response the response of the request
     * @returns the response of the request 
     */
    response(response){
        if (response.status === 401) {
            FetchInterceptor.theInstance.router.push('/sign-out');
        }
        return response;
    }
    // responseError(error){}
}