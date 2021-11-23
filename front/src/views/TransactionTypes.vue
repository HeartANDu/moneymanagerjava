<template>
    <main-layout>
        <h1 style="margin-bottom: 15px;">Transaction Types</h1>

        <types-list />
    </main-layout>
</template>

<script>
import MainLayout from "@/layouts/MainLayout";
import TypesList from "@/components/TransactionTypes/TypesList";

export default {
    name: "TransactionTypes",
    components: {TypesList, MainLayout}
}
</script>
