<template>
    <main-layout>
        <h1 class="route-title">Accounts</h1>

        <accounts-list />
    </main-layout>
</template>

<script>
import MainLayout from "@/layouts/MainLayout";
import AccountsList from "@/components/Accounts/AccountsList";
import CreateUpdateAccountDialog from "@/components/Accounts/CreateUpdateAccountDialog";

export default {
    name: "Accounts",
    components: {CreateUpdateAccountDialog, AccountsList, MainLayout}
}
</script>
