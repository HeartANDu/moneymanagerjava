<template>
    <v-data-table :headers="headers"
                  :items="data"
                  :loading="loading"
    >
        <template v-slot:footer.prepend>
            <create-update-account-dialog ref="cruDialog" @refresh="onRefresh" />
            <delete-account-dialog ref="deleteDialog" @refresh="onRefresh" />
        </template>
        <template v-slot:item.actions="{item}">
            <v-icon small
                    @click="showEditDialog(item)"
            >
                mdi-pencil
            </v-icon>
            <v-icon small
                    @click="showDeleteDialog(item)"
            >
                mdi-delete
            </v-icon>
        </template>
    </v-data-table>
</template>

<script>
import CreateUpdateAccountDialog from "@/components/Accounts/CreateUpdateAccountDialog";
import DeleteAccountDialog from "@/components/Accounts/DeleteAccountDialog";

export default {
    name: "AccountsList",
    components: {DeleteAccountDialog, CreateUpdateAccountDialog},
    data() {
        return {
            loading: false,
            data: [],
            headers: [
                {text: 'Account name', value: 'name'},
                {text: 'Current balance', value: 'balance'},
                {text: 'Account type', value: 'account_type.name'},
                {text: 'Actions', value: 'actions', sortable: false},
            ],
        };
    },
    methods: {
        onRefresh() {
            this.$store.dispatch('accounts/invalidate');
            this.load();
        },
        load() {
            this.$http.get("/accounts")
                .then(response => {
                    this.data = response.data;
                })
                .catch(this.$root.responseError)
                .finally(() => this.loading = false);
        },
        showEditDialog(item) {
            this.$refs.cruDialog.showEditDialog(item);
        },
        showDeleteDialog(item) {
            this.$refs.deleteDialog.showDialog(item);
        },
    },
    mounted() {
        this.load();
    }
}
</script>
