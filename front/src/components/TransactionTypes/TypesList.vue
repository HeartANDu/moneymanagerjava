<template>
    <v-data-table :headers="headers"
                  :items="data"
                  :loading="loading"
    >
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
        <template v-slot:footer.prepend>
            <create-update-type-dialog ref="cruDialog" @refresh="load" />
            <delete-type-dialog ref="deleteDialog" @refresh="load" />
        </template>
    </v-data-table>
</template>

<script>
import CreateUpdateTypeDialog from "@/components/TransactionTypes/CreateUpdateTypeDialog";
import DeleteTypeDialog from "@/components/TransactionTypes/DeleteTypeDialog";

export default {
    name: "TypesList",
    components: {DeleteTypeDialog, CreateUpdateTypeDialog},
    data() {
        return {
            loading: false,
            data: [],
            headers: [
                {text: 'Type Name', value: 'name'},
                {text: 'Type Action', value: 'action'},
                {text: 'Actions', value: 'actions', sortable: false},
            ],
        };
    },
    methods: {
        showEditDialog(item) {
            this.$refs.cruDialog.showDialog(item);
        },
        showDeleteDialog(item) {
            this.$refs.deleteDialog.showDialog(item);
        },
        load() {
            this.loading = true;
            this.$http.get('/transaction-types')
                .then(response => {
                    this.data = response.data;
                })
                .catch(this.$root.responseError)
                .finally(() => this.loading = false);
        },
    },
    mounted() {
        this.load();
    }
}
</script>
