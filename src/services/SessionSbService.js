export class SessionSbService {
    BROWSER_STORAGE_ITEM_NAME;
    RESOURCES_URL;

    constructor(resourcesUrl, browserStorageItemName) {
        this.RESOURCES_URL = resourcesUrl;
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.getTokenFromBrowserStorage();
    }



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
            return user;
        } else {
            console.log(response);
            return null;
        }
    }

    signOut() {
        sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
    }

    saveToken(token) {
        sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
    }
    getTokenFromBrowserStorage() {
        return sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
    }

}
