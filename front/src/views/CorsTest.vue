<template>
    <main-layout>
        <v-container>
            <v-row>
                <v-col>
                    <h4 v-if="message">{{ message }} <a href="#" @click.prevent="reset">Reset</a></h4>
                    <h4 v-else>No message yet. <a href="#" @click.prevent="getMessage">Get one</a></h4>
                </v-col>
            </v-row>
            <v-spacer />
            <v-row>
                <v-col>
                    <h4 v-if="adminmessage">{{ adminmessage }} <a href="#" @click.prevent="reset">Reset</a></h4>
                    <h4 v-else>No admin message yet. <a href="#" @click.prevent="getAdminMessage">Get one</a></h4>
                </v-col>
            </v-row>
        </v-container>

    </main-layout>
</template>

<script>
import MainLayout from "@/layouts/MainLayout";
export default {
    name: 'CorsTest',
    components: {MainLayout},
    data() {
        return {
            loading: false,
            message: null,
            adminmessage: null,
        };
    },
    methods: {
        getMessage() {
            this.$http.get('test/message')
                .then(response => {
                    console.log(response);
                    this.message = response.data.message;
                })
                .catch(this.$root.responseError);
        },
        getAdminMessage() {
            this.$http.get('test/adminmessage')
                .then(response => {
                    console.log(response);
                    this.adminmessage = response.data.message;
                })
                .catch(this.$root.responseError);
        },
        reset() {
            this.message = null;
        },
    }
}
</script>
