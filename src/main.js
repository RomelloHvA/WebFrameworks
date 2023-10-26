import 'bootstrap/dist/css/bootstrap.css';
import { createApp } from 'vue'
import AppComponent37 from "@/AppComponent37";
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import './main.css';
import router from './router';
import ToastPlugin from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-bootstrap.css';

createApp(AppComponent37).use(router).use(ToastPlugin).mount("#app");


