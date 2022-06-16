<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="client">
        <h2 class="jh-entity-heading" data-cy="clientDetailsHeading">
          <span v-text="$t('shopDailyReportApp.client.detail.title')">Client</span> {{ client.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.client.name')">Name</span>
          </dt>
          <dd>
            <span>{{ client.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.client.surName')">Sur Name</span>
          </dt>
          <dd>
            <span>{{ client.surName }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.client.debtAmount')">Debt Amount</span>
          </dt>
          <dd>
            <span>{{ client.debtAmount }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.client.address')">Address</span>
          </dt>
          <dd>
            <span>{{ client.address }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.client.phone')">Phone</span>
          </dt>
          <dd>
            <span>{{ client.phone }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.client.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ client.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.client.createdDate')">Created Date</span>
          </dt>
          <dd>
            <span v-if="client.createdDate">{{ $d(Date.parse(client.createdDate), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.client.code')">Code</span>
          </dt>
          <dd>
            <span>{{ client.code }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="client.id" :to="{ name: 'ClientEdit', params: { clientId: client.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./client-details.component.ts"></script>
