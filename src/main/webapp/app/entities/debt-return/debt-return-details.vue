<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="debtReturn">
        <h2 class="jh-entity-heading" data-cy="debtReturnDetailsHeading">
          <span v-text="$t('shopDailyReportApp.debtReturn.detail.title')">DebtReturn</span> {{ debtReturn.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.debtReturn.returnAmount')">Return Amount</span>
          </dt>
          <dd>
            <span>{{ debtReturn.returnAmount }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.debtReturn.returnDate')">Return Date</span>
          </dt>
          <dd>
            <span>{{ debtReturn.returnDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.debtReturn.code')">Code</span>
          </dt>
          <dd>
            <span>{{ debtReturn.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.debtReturn.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ debtReturn.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.debtReturn.client')">Client</span>
          </dt>
          <dd>
            <div v-if="debtReturn.client">
              <router-link :to="{ name: 'ClientView', params: { clientId: debtReturn.client.id } }">{{ debtReturn.client.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.debtReturn.paymentMethod')">Payment Method</span>
          </dt>
          <dd>
            <div v-if="debtReturn.paymentMethod">
              <router-link :to="{ name: 'PaymentMethodView', params: { paymentMethodId: debtReturn.paymentMethod.id } }">{{
                debtReturn.paymentMethod.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="debtReturn.id"
          :to="{ name: 'DebtReturnEdit', params: { debtReturnId: debtReturn.id } }"
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

<script lang="ts" src="./debt-return-details.component.ts"></script>
