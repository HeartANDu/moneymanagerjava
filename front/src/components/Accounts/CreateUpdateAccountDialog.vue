<template>
    <v-dialog v-model="show" max-width="400">
        <template v-slot:activator="{on, attrs}">
            <v-btn color="primary"
                   v-bind="attrs"
                   @click="showEditDialog()"
            >
                {{ actionText }}
            </v-btn>
        </template>

        <v-card>
            <v-card-title>
                {{ actionText }} account
            </v-card-title>

            <v-card-text>
                <v-form ref="form">
                    <v-text-field label="Name"
                                  v-model="innerAccount.name"
                                  required
                    />

                    <v-select label="Account Type"
                              :items="accountTypes"
                              v-model="innerAccount.accountType"
                              item-text="name"
                              item-value="id"
                    />
                </v-form>
            </v-card-text>

            <v-divider />

            <v-card-actions>
                <v-btn color="default"
                       text
                       @click="show = false"
                >
                    Close
                </v-btn>
                <v-spacer />
                <v-btn color="primary"
                       text
                       @click="save"
                >
                    Save
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
import {clone} from 'lodash';

const DEFAULT_ACCOUNT = {
    id: null,
    name: null,
    accountType: null,
};

export default {
    name: "CreateUpdateAccountDialog",
    computed: {
        actionText() {
            return this.account ? 'Edit' : 'Create';
        },
        accountTypes() {
            return this.$store.state.accountTypes.items;
        },
    },
    data() {
        return {
            show: false,
            innerAccount: clone(DEFAULT_ACCOUNT),
        };
    },
    methods: {
        reset() {
            this.innerAccount = clone(DEFAULT_ACCOUNT);
        },
        initInnerAccount(account) {
            if (!account) {
                this.reset();
            } else {
                this.innerAccount.id = account.id;
                this.innerAccount.name = account.name;
                this.innerAccount.accountType = account.account_type.id;
            }
        },
        showEditDialog(account) {
            this.show = true;
            this.initInnerAccount(account);
        },
        save() {
            this.loading = true;

            let id = this.innerAccount.id;
            let data = {name: this.innerAccount.name, account_type_id: this.innerAccount.accountType};
            this.$http.request({method: id ? 'put' : 'post', url: id ? `/accounts/${id}` : '/accounts', data})
                .then(() => {
                    this.$root.success('Account added successfully');
                    this.$emit('refresh');
                    this.show = false;
                })
                .catch(this.$root.responseError)
                .finally(() => this.loading = false);
        },
    },
    mounted() {
        this.$store.dispatch('accountTypes/init');
    },
}
</script>
