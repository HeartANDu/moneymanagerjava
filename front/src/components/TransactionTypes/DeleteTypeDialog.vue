<template>
    <v-dialog v-model="show" max-width="400">
        <v-card>
            <v-card-title class="text-center" style="word-break: keep-all;">
                Are you sure you want to delete transaction type "{{ typeName }}"?
            </v-card-title>
            <v-card-actions>
                <v-btn color="default" text @click="hideDialog">Cancel</v-btn>
                <v-spacer />
                <v-btn color="red" text @click="deleteType" :disabled="loading">Delete</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
export default {
    name: "DeleteTypeDialog",
    data() {
        return {
            loading: false,
            show: false,
            type: null,
        };
    },
    computed: {
        typeName() {
            return this.type?.name;
        },
    },
    methods: {
        showDialog(type) {
            this.show = true;
            this.type = type;
        },
        hideDialog() {
            this.show = false;
            this.type = null;
        },
        deleteType() {
            this.loading = true;
            this.$http.delete(`/transaction-types/${this.type.id}`)
                .then(() => {
                    this.$root.success(`Transaction type "${this.type.name}" deleted successfully`);
                    this.$emit('refresh');
                    this.hideDialog();
                })
                .catch(this.$root.responseError)
                .finally(() => this.loading = false);
        },
    }
}
</script>
