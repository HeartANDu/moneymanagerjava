<template>
    <v-navigation-drawer app>
        <v-list>
            <v-list-item>
                <v-list-item-avatar>
                    <img src="../assets/default-user.png" alt="avatar" />
                </v-list-item-avatar>
            </v-list-item>
            <v-list-item link>
                <v-list-item-content>
                    <v-list-item-title class="text-h6">
                        {{ user.username }}
                    </v-list-item-title>
                    <v-list-item-subtitle>{{ user.email }}</v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>
        </v-list>
        <v-divider />
        <v-list nav dense>
            <v-list-item-group color="primary">
                <v-list-item v-for="(item, i) in links" :key="i" @click="goTo(item.to)">
                    <v-list-item-content>
                        <v-list-item-title v-text="item.name" />
                    </v-list-item-content>
                </v-list-item>

                <v-list-item v-if="$auth.check()" @click="logout">
                    <v-list-item-content>
                        <v-list-item-title>Log Out</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list-item-group>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
export default {
    name: "NavigationLayout",
    data() {
        return {
            links: [
                {to: '/', name: 'Home'},
                {to: '/accounts', name: 'Accounts'},
                {to: '/transaction-types', name: 'Transaction Types'},
            ],
        };
    },
    computed: {
        user() {
            return this.$auth.user() ?? {};
        }
    },
    methods: {
        goTo(to) {
            if (this.$router.currentRoute.path !== to) {
                this.$router.push(to);
            }
        },
        logout() {
            this.$auth.logout({
                makeRequest: true,
                redirect: '/login',
            });
        }
    },
}
</script>

<style scoped>

</style>