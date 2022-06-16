<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="expenseType">
        <h2 class="jh-entity-heading" data-cy="expenseTypeDetailsHeading">
          <span v-text="$t('shopDailyReportApp.expenseType.detail.title')">ExpenseType</span> {{ expenseType.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.expenseType.name')">Name</span>
          </dt>
          <dd>
            <span>{{ expenseType.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.expenseType.code')">Code</span>
          </dt>
          <dd>
            <span>{{ expenseType.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.expenseType.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ expenseType.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.expenseType.createdDate')">Created Date</span>
          </dt>
          <dd>
            <span>{{ expenseType.createdDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="expenseType.id"
          :to="{ name: 'ExpenseTypeEdit', params: { expenseTypeId: expenseType.id } }"
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

<script lang="ts" src="./expense-type-details.component.ts"></script>
