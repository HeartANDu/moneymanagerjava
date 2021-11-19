<template>
    <auth-layout>
        <v-container>
            <v-row>
                <v-col>
                    <h2 class="text-center">{{ error }}</h2>
                </v-col>
            </v-row>
            <v-spacer />
            <v-row class="text-center">
                <v-col>
                    <v-btn color="primary"
                           large
                           text
                           rounded
                           @click.prevent="$router.push('/')"
                    >
                        Return to Home
                    </v-btn>
                </v-col>
            </v-row>
        </v-container>
    </auth-layout>
</template>

<script>
import AuthLayout from "@/layouts/AuthLayout";

export default {
    name: "Error",
    components: {AuthLayout},
    props: {
        error: {
            required: true,
            type: String,
        },
    },
}
</script>

<style scoped>

</style>