<template>
    <auth-layout>
        <v-form ref="form">
            <v-text-field label="Username"
                          prepend-inner-icon="mdi-account"
                          v-model.trim="form.username"
                          required
                          :rules="[$v.required(), $v.between(3, 50)]"
            />

            <v-text-field label="Email"
                          prepend-inner-icon="mdi-email"
                          v-model.trim="form.email"
                          required
                          :rules="[$v.required(), $v.maxLength(100), $v.email()]"
            />

            <v-text-field label="Password"
                          prepend-inner-icon="mdi-lock"
                          type="password"
                          v-model.trim="form.password"
                          required
                          :rules="[$v.required(), $v.between(6, 40)]"
            />

            <v-text-field label="Confirm Password"
                          prepend-inner-icon="mdi-lock"
                          type="password"
                          v-model.trim="form.confirmPassword"
                          :rules="[$v.sameAs('Password', form.password)]"
            />

            <div class="text-center">
                <v-btn :loading="loading"
                       color="primary"
                       large
                       @click.prevent="register"
                       text
                       rounded
                >Register</v-btn>
            </div>
            <div class="text-center">
                <router-link to="/login" style="text-decoration: none;">
                    <v-btn color="primary"
                           large
                           text
                           rounded
                    >Go To Login</v-btn>
                </router-link>
            </div>
        </v-form>
    </auth-layout>
</template>

<script>
import AuthLayout from "@/layouts/AuthLayout";
import validation from '@/mixins/validation';

export default {
    name: "Register",
    components: {AuthLayout},
    mixins: [validation],
    data() {
        return {
            loading: false,
            form: {
                username: null,
                email: null,
                password: null,
                confirmPassword: null,
            },
        };
    },
    methods: {
        register() {
            if (this.$refs.form.validate()) {
                this.loading = true;
                this.$auth.register({data: this.form})
                    .then(() => this.$root.success('Registration successful'))
                    .catch(this.$root.responseError)
                    .finally(() => this.loading = false);
            }
        },
    },
}
</script>

<style scoped>

</style>