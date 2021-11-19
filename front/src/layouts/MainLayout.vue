<template>
    <v-container>
        <navigation-layout />

        <v-main>
            <v-container fluid>
                <slot />
            </v-container>
        </v-main>
    </v-container>
</template>

<script>
import NavigationLayout from "@/layouts/NavigationLayout";
export default {
    name: "MainLayout",
    components: {NavigationLayout}
}
</script>

<style scoped>

</style>