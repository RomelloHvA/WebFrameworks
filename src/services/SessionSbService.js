import { ref } from "vue";

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

    signOut() {
        sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
        sessionStorage.removeItem('user');
        this.user.value = null
    }

    saveToken(token, user) {
        sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
        sessionStorage.setItem('user', JSON.stringify(user));
    }
    getTokenFromBrowserStorage() {
        return sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
    }
    getUserFromBrowserStorage() {
        return JSON.parse(sessionStorage.getItem('user'));
    }

}
