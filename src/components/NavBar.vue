/** This is the NavBar component it creates the navigation bar on the top of the page.  
 *  @name NavBar
 *  @author Marco de Boer

*/


<template>
    <nav class="navbar navbar-expand-lg bg-dark ">
        <div class="container-fluid ">
            <a class="navbar-brand text-light" href="#">Electric Scooter</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <router-link to="/" active-class="nav-item-active">
                        <li class="nav-item"><a class="nav-link" aria-current="page" href="#">Home</a></li>
                    </router-link>
                    <li class="nav-item dropdown" :class="{ 'nav-item-active': $route.path.startsWith('/scooters') }">
                        <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">Scooters</a>
                        <ul class="dropdown-menu bg-dark">
                          <li><router-link exact-active-class="dropdown-item-active" :to=" { name: 'scooterOverview31' }" class="dropdown-item">Scooters overview</router-link></li>
                          <li><router-link exact-active-class="dropdown-item-active" :to=" { name: 'scooterOverview32' }" class="dropdown-item">Scooters details (comp)</router-link></li>
                          <li><router-link exact-active-class="dropdown-item-active" :to=" { name: 'scooterOverview33' }" class="dropdown-item">Scooters details (router)</router-link></li>
                            <li><a class="dropdown-item" href="#">Available Scooters</a></li>
                            <li><a class="dropdown-item" href="#">Abandoned Scooters</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">My Trips</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">My Account</a>
                    </li>
                </ul>
                <div class="justify-content-end">
                   <div class="d-flex gap-2">
                        <router-link to="/sign-up"><button type="button row" class="btn btn-outline-primary">Sign up</button></router-link>
                        <router-link to="/sign-in"><button type="button row" class="btn btn-outline-light">Login</button></router-link>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</template>

<script>

export default {
    name: "NavBar",

}
</script>

<style>

  nav{
    width: 100%;
  }
   .nav-link{
       color: white !important;
   }

   .dropdown-item{
       color: white !important;
   }

   .dropdown-menu{
        border: none !important;
        background-color: #343a40 !important;
        margin: 0 !important;
   }

   .dropdown-item:hover, .dropdown-item-active {
       background-color: #131517 !important;
   }

   .nav-item:hover, .nav-item-active {
       background-color: #343a40 !important;
       border-radius: 5px;
   }

   .navbar-toggler-icon {
        background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='30' height='30' viewBox='0 0 30 30'%3e%3cpath stroke='rgba(255, 255, 255, 1)' stroke-linecap='round' stroke-miterlimit='10' stroke-width='2' d='M4 7h22M4 15h22M4 23h22'/%3e%3c/svg%3e");
    }

    .navbar-toggler {
        border: var(--bs-border-width) solid white !important;
    }

    .navbar-toggler:focus {
        outline: 2px solid white !important;
        box-shadow: none !important;
    }





</style>