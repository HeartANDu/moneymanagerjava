<template>
    <v-dialog v-model="show" max-width="600">
        <template v-slot:activator="{on, attrs}">
            <v-btn color="primary"
                   v-bind="attrs"
                   @click="showDialog()"
            >
                Add Transaction
            </v-btn>
        </template>

        <v-card>
            <v-card-title>
                {{ actionText }} transaction
            </v-card-title>

            <v-card-text>
                <v-form ref="form">
                    <v-container>
                        <v-row>
                            <v-col cols="12" sm="4">
                                <date-picker v-model="transaction.date" label="Transaction date" />
                            </v-col>
                            <v-col cols="12" sm="4">
                                <v-select label="Action"
                                          :items="transactionActions"
                                          v-model="transaction.action"
                                />
                            </v-col>
                            <v-col cols="12" sm="4">
                                <v-select label="Transaction Type"
                                          :items="transactionTypes"
                                          v-model="transaction.transactionTypeId"
                                          item-text="name"
                                          item-value="id"
                                          no-data-text="Select Action first"
                                />
                            </v-col>
                        </v-row>

                        <v-row>
                            <v-col cols="12" sm="6">
                                <v-select label="Account"
                                          :items="accounts"
                                          v-model="transaction.accountId"
                                          item-text="name"
                                          item-value="id"
                                />
                            </v-col>
                            <v-col cols="12" sm="6">
                                <v-text-field label="Amount"
                                              v-model.number="transaction.amount"
                                              required
                                />
                            </v-col>
                        </v-row>

                        <v-row>
                            <v-col>
                                <v-text-field label="Comment"
                                              v-model="transaction.comment"
                                />
                            </v-col>
                        </v-row>
                    </v-container>
                </v-form>
            </v-card-text>

            <v-divider />

            <v-card-actions>
                <v-btn color="default"
                       text
                       @click="hideDialog"
                >
                    Close
                </v-btn>
                <v-spacer />
                <v-btn color="primary"
                       text
                       @click="save"
                       :loading="loading"
                >
                    Save
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
import {clone, filter} from 'lodash';
import DatePicker from "@/components/Common/DatePicker";

const TRANSACTION_TEMPLATE = {
    id: null,
    date: null,
    action: null,
    amount: null,
    comment: null,
    transactionTypeId: null,
    accountId: null,
};

const URL = '/transactions';

export default {
    name: "CreateOrUpdateTransactionDialog",
    components: {DatePicker},
    data() {
        return {
            loading: false,
            show: false,
            transaction: clone(TRANSACTION_TEMPLATE),
        };
    },
    computed: {
        actionText() {
            return this.transaction?.id ? 'Edit' : 'Add';
        },
        accounts() {
            return this.$store.state.accounts.items;
        },
        transactionTypes() {
            return filter(this.$store.state.transactionTypes.items, i => i.action === this.transaction.action);
        },
        transactionActions() {
            return this.$store.state.transactionActions.items;
        },
    },
    methods: {
        reset() {
            this.transaction = clone(TRANSACTION_TEMPLATE);
        },
        initTransaction(transaction) {
            if (!transaction) {
                this.reset();
            } else {
                this.transaction.id = transaction.id;
                this.transaction.date = transaction.date;
                this.transaction.action = transaction.action;
                this.transaction.amount = Math.abs(transaction.amount);
                this.transaction.comment = transaction.comment;
                this.transaction.transactionTypeId = transaction.type.id;
                this.transaction.accountId = transaction.account.id;
            }
        },
        showDialog(transaction) {
            this.initTransaction(transaction);
            this.show = true;
        },
        hideDialog() {
            this.reset();
            this.show = false;
        },

        save() {
            this.loading = true;

            let id = this.transaction.id;
            let data = {
                date: this.transaction.date,
                action: this.transaction.action,
                amount: this.transaction.amount,
                comment: this.transaction.comment,
                transaction_type_id: this.transaction.transactionTypeId,
                account_id: this.transaction.accountId,
            };
            let url = id ? URL + `/${id}` : URL;
            let method = id ? 'put' : 'post';

            this.$http.request({method, url, data})
                .then(() => {
                    this.$root.success('Transaction saved successfully');
                    this.$emit('refresh');
                    this.hideDialog();
                })
                .catch(this.$root.responseError)
                .finally(() => this.loading = false);
        },
    },
    mounted() {
        this.$store.dispatch('accounts/init');
        this.$store.dispatch('transactionActions/init');
        this.$store.dispatch('transactionTypes/init');
    },
}
</script>
