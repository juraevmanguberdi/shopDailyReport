<template>
  <div>
    <b-dropdown-item to="/debt-given">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.debtGiven')">Debt Given</span>
    </b-dropdown-item>
    <b-dropdown-item to="/debt-return">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.debtReturn')">Debt Return</span>
    </b-dropdown-item>
    <b-dropdown-item to="/expense">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.expense')">Expense</span>
    </b-dropdown-item>
    <b-dropdown-item to="/expense-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.expenseType')">Expense Type</span>
    </b-dropdown-item>
    <b-dropdown-item to="/daily-registry-shop">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.dailyRegistryShop')">Daily Registry Shop</span>
    </b-dropdown-item>
    <b-dropdown-item to="/owner-expense">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.ownerExpense')">Owner Expense</span>
    </b-dropdown-item>
    <b-dropdown-item to="/owner-expense-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.ownerExpenseType')">Owner Expense Type</span>
    </b-dropdown-item>
    <b-dropdown-item to="/client">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.client')">Client</span>
    </b-dropdown-item>
    <b-dropdown-item to="/payment-method">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.paymentMethod')">Payment Method</span>
    </b-dropdown-item>
    <b-dropdown-item to="/asset-registry">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.assetRegistry')">Asset Registry</span>
    </b-dropdown-item>
    <b-dropdown-item to="/liability-registry">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.liabilityRegistry')">Liability Registry</span>
    </b-dropdown-item>
    <b-dropdown-item to="/required-product">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.requiredProduct')">Required Product</span>
    </b-dropdown-item>
    <b-dropdown-item to="/required-product-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.requiredProductType')">Required Product Type</span>
    </b-dropdown-item>
    <b-dropdown-item to="/borrowed-debt">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.borrowedDebt')">Borrowed Debt</span>
    </b-dropdown-item>
    <b-dropdown-item to="/borrowed-debt-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.borrowedDebtType')">Borrowed Debt Type</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
