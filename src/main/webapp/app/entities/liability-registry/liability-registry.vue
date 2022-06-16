<template>
  <div>
    <h2 id="page-heading" data-cy="LiabilityRegistryHeading">
      <span v-text="$t('shopDailyReportApp.liabilityRegistry.home.title')" id="liability-registry-heading">Liability Registries</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopDailyReportApp.liabilityRegistry.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'LiabilityRegistryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-liability-registry"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopDailyReportApp.liabilityRegistry.home.createLabel')"> Create a new Liability Registry </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && liabilityRegistries && liabilityRegistries.length === 0">
      <span v-text="$t('shopDailyReportApp.liabilityRegistry.home.notFound')">No liabilityRegistries found</span>
    </div>
    <div class="table-responsive" v-if="liabilityRegistries && liabilityRegistries.length > 0">
      <table class="table table-striped" aria-describedby="liabilityRegistries">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('today')">
              <span v-text="$t('shopDailyReportApp.liabilityRegistry.today')">Today</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'today'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('totalLiabilities')">
              <span v-text="$t('shopDailyReportApp.liabilityRegistry.totalLiabilities')">Total Liabilities</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'totalLiabilities'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('supplier')">
              <span v-text="$t('shopDailyReportApp.liabilityRegistry.supplier')">Supplier</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'supplier'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('bankLoan')">
              <span v-text="$t('shopDailyReportApp.liabilityRegistry.bankLoan')">Bank Loan</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'bankLoan'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('other')">
              <span v-text="$t('shopDailyReportApp.liabilityRegistry.other')">Other</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'other'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('shopDailyReportApp.liabilityRegistry.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('notes')">
              <span v-text="$t('shopDailyReportApp.liabilityRegistry.notes')">Notes</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'notes'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="liabilityRegistry in liabilityRegistries" :key="liabilityRegistry.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LiabilityRegistryView', params: { liabilityRegistryId: liabilityRegistry.id } }">{{
                liabilityRegistry.id
              }}</router-link>
            </td>
            <td>{{ liabilityRegistry.today }}</td>
            <td>{{ liabilityRegistry.totalLiabilities }}</td>
            <td>{{ liabilityRegistry.supplier }}</td>
            <td>{{ liabilityRegistry.bankLoan }}</td>
            <td>{{ liabilityRegistry.other }}</td>
            <td>{{ liabilityRegistry.code }}</td>
            <td>{{ liabilityRegistry.notes }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'LiabilityRegistryView', params: { liabilityRegistryId: liabilityRegistry.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'LiabilityRegistryEdit', params: { liabilityRegistryId: liabilityRegistry.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(liabilityRegistry)"
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
        ><span
          id="shopDailyReportApp.liabilityRegistry.delete.question"
          data-cy="liabilityRegistryDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-liabilityRegistry-heading" v-text="$t('shopDailyReportApp.liabilityRegistry.delete.question', { id: removeId })">
          Are you sure you want to delete this Liability Registry?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-liabilityRegistry"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeLiabilityRegistry()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="liabilityRegistries && liabilityRegistries.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./liability-registry.component.ts"></script>
