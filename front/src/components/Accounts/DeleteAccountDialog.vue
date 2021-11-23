<template>
    <v-dialog v-model="show" max-width="400">
        <v-card>
            <v-card-title class="text-center" style="word-break: keep-all;">
                Are you sure you want to delete account "{{ accountName }}"?
            </v-card-title>
            <v-card-actions>
                <v-btn color="default" text @click="hideDialog">Cancel</v-btn>
                <v-spacer />
                <v-btn color="red" text @click="deleteAccount" :disabled="loading">Delete</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
export default {
    name: "DeleteAccountDialog",
    data() {
        return {
            show: false,
            loading: false,
            account: null,
        };
    },
    computed: {
        accountName() {
            return this.account?.name;
        },
    },
    methods: {
        hideDialog() {
            this.show = false;
            this.account = null;
        },
        showDialog(account) {
            this.account = account;
            this.show = true;
        },
        deleteAccount() {
            this.loading = true;
            this.$http.delete(`/accounts/${this.account.id}`)
                .then(() => {
                    this.$root.success(`Account "${this.account.name}" deleted successfully`);
                    this.$emit('refresh');
                    this.hideDialog();
                })
                .catch(this.$root.responseError)
                .finally(() => this.loading = false);
        },
    }
}
</script>
