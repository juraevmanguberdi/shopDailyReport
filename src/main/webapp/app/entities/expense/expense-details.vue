<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="expense">
        <h2 class="jh-entity-heading" data-cy="expenseDetailsHeading">
          <span v-text="$t('shopDailyReportApp.expense.detail.title')">Expense</span> {{ expense.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.expense.amount')">Amount</span>
          </dt>
          <dd>
            <span>{{ expense.amount }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.expense.expenseDate')">Expense Date</span>
          </dt>
          <dd>
            <span>{{ expense.expenseDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.expense.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ expense.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.expense.code')">Code</span>
          </dt>
          <dd>
            <span>{{ expense.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.expense.expenseType')">Expense Type</span>
          </dt>
          <dd>
            <div v-if="expense.expenseType">
              <router-link :to="{ name: 'ExpenseTypeView', params: { expenseTypeId: expense.expenseType.id } }">{{
                expense.expenseType.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="expense.id" :to="{ name: 'ExpenseEdit', params: { expenseId: expense.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./expense-details.component.ts"></script>
