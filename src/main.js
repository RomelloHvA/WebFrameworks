import 'bootstrap/dist/css/bootstrap.css';
import { createApp } from 'vue'
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import './main.css';
import router from './router';
import ToastPlugin from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-bootstrap.css';
import AppComponent44 from "@/AppComponent44";

createApp(AppComponent44).use(router).use(ToastPlugin).mount("#app");


