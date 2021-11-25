<template>
    <v-data-table :headers="headers"
                  :items="data"
                  :loading="loading"
                  :options.sync="pagination"
                  :server-items-length="totalPaginationElements"
                  multi-sort
    >
        <template v-slot:top>
            <v-toolbar flat>
                <v-toolbar-title>Latest Transactions</v-toolbar-title>
                <v-spacer />
                <create-or-update-transaction-dialog ref="cruDialog" @refresh="load" />
            </v-toolbar>
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
import CreateOrUpdateTransactionDialog from "@/components/Transactions/CreateOrUpdateTransactionDialog";
import {map} from 'lodash';

export default {
    name: "LatestTransactions",
    components: {CreateOrUpdateTransactionDialog},
    data() {
        return {
            loading: false,
            headers: [
                {text: 'Date', value: 'date'},
                {text: 'Account', value: 'account.name'},
                {text: 'Transaction Type', value: 'type.name'},
                {text: 'Amount', value: 'amount'},
                {text: 'Comment', value: 'comment'},
                {text: 'Actions', value: 'actions', sortable: false},
            ],
            data: [],
            pagination: {
                page: 1,
                itemsPerPage: 5,
            },
            totalPaginationElements: 1,
        };
    },
    watch: {
        pagination: {
            handler() {
                this.load();
            },
            deep: true,
        }
    },
    computed: {
        pageParams() {
            return {
                page: this.pagination.page - 1,
                size: this.pagination.itemsPerPage,
                sort: map(this.pagination.sortBy, (val, i) => {
                    return `${val},${this.pagination.sortDesc[i] ? 'desc' : 'asc'}`;
                }),
            }
        },
    },
    methods: {
        showEditDialog(transaction) {
            this.$refs.cruDialog.showDialog(transaction);
        },
        showDeleteDialog(transaction) {
            this.$root.confirm(
                'Are you sure you want to delete selected transaction?',
                null,
                () => {
                    this.$http.delete(`/transactions/${transaction.id}`)
                        .then(() => {
                            this.$root.success('Transaction deleted successfully');
                            this.load();
                        })
                        .catch(this.$root.responseError);
                }
            );
        },
        load() {
            if (this.loading) {
                return;
            }

            this.loading = true;

            this.$http.get('/transactions', {params: this.pageParams})
                .then(response => {
                    this.data = response.data.content;
                    this.totalPaginationElements = response.data.totalElements;
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
