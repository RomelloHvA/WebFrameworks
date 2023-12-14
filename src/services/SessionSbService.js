import { ref } from "vue";

/** Class representing a session service.
 * It manages the session of the user. 
 * It saves the token in the browser storage.
 * It retrieves the token from the browser storage.
 * @param {string} resourcesUrl 
 * @param {string} browserStorageItemName
 * @param {User} user is a ref for the user
 * @returns {SessionSbService} the instance of the class
 */
export class SessionSbService {
    BROWSER_STORAGE_ITEM_NAME;
    RESOURCES_URL;
    user

    constructor(resourcesUrl, browserStorageItemName) {
        this.RESOURCES_URL = resourcesUrl;
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.getTokenFromBrowserStorage();
        this.user = ref(null);
    }


    /**
     *  It sends a request to the server to sign up the user.
     * @param {String} email 
     * @param {String} password 
     * @returns {Promise<User>} the user signed up
     */
    async asyncSignIn(email, password) /*: Promise<User>*/ {
        const response = await fetch(`${this.RESOURCES_URL}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email: email, password: password }),
            credentials: 'include',
        });
        if (response.ok) {
            let user = await response.json();
            this.saveToken(response.headers.get('Authorization'),
                user);
            this.user.value = user;
            return { user, response };
        } else {
            console.log(response);
            let status = response.status;
            return { user: null, status};
        }
    }
    /**
     * On signout it removes the token from the browser storage.
     * It removes the user from the browser storage.
     * It sets the user to null.
     */
    signOut() {
        sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
        sessionStorage.removeItem('user');
        this.user.value = null
    }

    /**
     * It saves the token in the browser storage.
     * @param {String} token jwt Token 
     * @param {Object} user  
     */
    saveToken(token, user) {
        sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
        sessionStorage.setItem('user', JSON.stringify(user));
    }
    /**
     * It retrieves the token from the browser storage.
     * @returns {String} the token
     */
    getTokenFromBrowserStorage() {
        return sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
    }
    /**
     * It retrieves the user from the browser storage.
     * @returns {Object} the user
     */
    getUserFromBrowserStorage() {
        return JSON.parse(sessionStorage.getItem('user'));
    }

}
