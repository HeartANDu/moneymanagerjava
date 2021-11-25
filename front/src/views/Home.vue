<template>
    <main-layout>
        <home-page />
    </main-layout>
</template>

<script>
import MainLayout from "@/layouts/MainLayout";
import HomePage from "@/components/Home/HomePage";

export default {
    name: 'Home',
    components: {
        HomePage,
        MainLayout,
    }
}
</script>
