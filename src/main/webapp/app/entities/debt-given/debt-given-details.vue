<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="debtGiven">
        <h2 class="jh-entity-heading" data-cy="debtGivenDetailsHeading">
          <span v-text="$t('shopDailyReportApp.debtGiven.detail.title')">DebtGiven</span> {{ debtGiven.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.debtGiven.debtAmount')">Debt Amount</span>
          </dt>
          <dd>
            <span>{{ debtGiven.debtAmount }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.debtGiven.debtDate')">Debt Date</span>
          </dt>
          <dd>
            <span>{{ debtGiven.debtDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.debtGiven.returnDate')">Return Date</span>
          </dt>
          <dd>
            <span>{{ debtGiven.returnDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.debtGiven.code')">Code</span>
          </dt>
          <dd>
            <span>{{ debtGiven.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.debtGiven.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ debtGiven.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.debtGiven.client')">Client</span>
          </dt>
          <dd>
            <div v-if="debtGiven.client">
              <router-link :to="{ name: 'ClientView', params: { clientId: debtGiven.client.id } }">{{ debtGiven.client.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="debtGiven.id"
          :to="{ name: 'DebtGivenEdit', params: { debtGivenId: debtGiven.id } }"
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

<script lang="ts" src="./debt-given-details.component.ts"></script>
