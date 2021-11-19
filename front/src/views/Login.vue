<template>
    <auth-layout>
        <v-form ref="form">
            <v-text-field label="Username"
                          prepend-inner-icon="mdi-account"
                          v-model="form.username"
                          required
                          :rules="[$v.required()]"
            />

            <v-text-field label="Password"
                          prepend-inner-icon="mdi-lock"
                          type="password"
                          v-model="form.password"
                          @keydown.enter="login"
                          :rules="[$v.required()]"
            />

            <div class="text-center">
                <v-btn :loading="loading"
                       color="primary"
                       large
                       @click.prevent="login"
                       text
                       rounded
                >Sign In</v-btn>
            </div>
            <div class="text-center">
                <router-link to="/register" style="text-decoration: none;">
                    <v-btn color="primary"
                           large
                           text
                           rounded
                    >Register</v-btn>
                </router-link>

            </div>
        </v-form>
    </auth-layout>
</template>

<script>
import AuthLayout from "@/layouts/AuthLayout";
import validation from "@/mixins/validation";

export default {
    name: "Login",
    components: {AuthLayout},
    mixins: [validation],
    data() {
        return {
            loading: false,
            form: {
                username: null,
                password: null,
            },
        };
    },
    methods: {
        login() {
            if (this.$refs.form.validate()) {
                this.loading = true;
                let data = {
                    data: this.form,
                    redirect: {name: 'Home'},
                };
                this.$auth.login(data)
                    .catch(this.$root.responseError)
                    .finally(() => this.loading = false);
            }
        },
    },
}
</script>

<style scoped>

</style>