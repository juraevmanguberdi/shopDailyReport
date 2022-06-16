<template>
  <div>
    <h2 id="page-heading" data-cy="DebtGivenHeading">
      <span v-text="$t('shopDailyReportApp.debtGiven.home.title')" id="debt-given-heading">Debt Givens</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopDailyReportApp.debtGiven.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DebtGivenCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-debt-given"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopDailyReportApp.debtGiven.home.createLabel')"> Create a new Debt Given </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && debtGivens && debtGivens.length === 0">
      <span v-text="$t('shopDailyReportApp.debtGiven.home.notFound')">No debtGivens found</span>
    </div>
    <div class="table-responsive" v-if="debtGivens && debtGivens.length > 0">
      <table class="table table-striped" aria-describedby="debtGivens">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('debtAmount')">
              <span v-text="$t('shopDailyReportApp.debtGiven.debtAmount')">Debt Amount</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'debtAmount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('debtDate')">
              <span v-text="$t('shopDailyReportApp.debtGiven.debtDate')">Debt Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'debtDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('returnDate')">
              <span v-text="$t('shopDailyReportApp.debtGiven.returnDate')">Return Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'returnDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('shopDailyReportApp.debtGiven.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('client.id')">
              <span v-text="$t('shopDailyReportApp.debtGiven.client')">Client</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'client.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="debtGiven in debtGivens" :key="debtGiven.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DebtGivenView', params: { debtGivenId: debtGiven.id } }">{{ debtGiven.id }}</router-link>
            </td>
            <td>{{ debtGiven.debtAmount }}</td>
            <td>{{ debtGiven.debtDate }}</td>
            <td>{{ debtGiven.returnDate }}</td>
            <td>{{ debtGiven.code }}</td>
            <td>
              <div v-if="debtGiven.client">
                <router-link :to="{ name: 'ClientView', params: { clientId: debtGiven.client.id } }">{{ debtGiven.client.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DebtGivenView', params: { debtGivenId: debtGiven.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DebtGivenEdit', params: { debtGivenId: debtGiven.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(debtGiven)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="shopDailyReportApp.debtGiven.delete.question" data-cy="debtGivenDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-debtGiven-heading" v-text="$t('shopDailyReportApp.debtGiven.delete.question', { id: removeId })">
          Are you sure you want to delete this Debt Given?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-debtGiven"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDebtGiven()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="debtGivens && debtGivens.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./debt-given.component.ts"></script>
