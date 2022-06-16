<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ownerExpense">
        <h2 class="jh-entity-heading" data-cy="ownerExpenseDetailsHeading">
          <span v-text="$t('shopDailyReportApp.ownerExpense.detail.title')">OwnerExpense</span> {{ ownerExpense.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.ownerExpense.amount')">Amount</span>
          </dt>
          <dd>
            <span>{{ ownerExpense.amount }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.ownerExpense.code')">Code</span>
          </dt>
          <dd>
            <span>{{ ownerExpense.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.ownerExpense.expenseDate')">Expense Date</span>
          </dt>
          <dd>
            <span>{{ ownerExpense.expenseDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.ownerExpense.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ ownerExpense.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.ownerExpense.ownerExpenseType')">Owner Expense Type</span>
          </dt>
          <dd>
            <div v-if="ownerExpense.ownerExpenseType">
              <router-link :to="{ name: 'OwnerExpenseTypeView', params: { ownerExpenseTypeId: ownerExpense.ownerExpenseType.id } }">{{
                ownerExpense.ownerExpenseType.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="ownerExpense.id"
          :to="{ name: 'OwnerExpenseEdit', params: { ownerExpenseId: ownerExpense.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./owner-expense-details.component.ts"></script>
