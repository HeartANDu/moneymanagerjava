<template>
    <main-layout>
        <div class="home">
            <img alt="Vue logo" src="../assets/logo.png">
            <HelloWorld msg="Welcome to Your Vue.js App"/>
        </div>
    </main-layout>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import MainLayout from "@/layouts/MainLayout";

export default {
    name: 'Home',
    components: {
        MainLayout,
        HelloWorld
    }
}
</script>
