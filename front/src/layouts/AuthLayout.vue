<template>
    <v-container fluid full-height>
        <v-layout align-center justify-center>
            <v-flex :style="{ 'max-width': '350px' }">
                <v-card>
                    <v-card-text>
                        <transition name="fade" mode="out-in">
                            <slot />
                        </transition>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
export default {
    name: "AuthLayout"
}
</script>

<style scoped>

</style>