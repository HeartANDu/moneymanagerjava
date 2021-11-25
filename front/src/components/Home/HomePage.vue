<template>
    <div>
        <h1 class="route-title">Welcome to Money Manager!</h1>

        <latest-transactions />
    </div>
</template>

<script>
import LatestTransactions from "@/components/Transactions/LatestTransactions";
export default {
    name: "HomePage",
    components: {LatestTransactions},
}
</script>
