<template>
  <div>
    <h2 id="page-heading" data-cy="ClientHeading">
      <span v-text="$t('shopDailyReportApp.client.home.title')" id="client-heading">Clients</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopDailyReportApp.client.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ClientCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-client"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopDailyReportApp.client.home.createLabel')"> Create a new Client </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && clients && clients.length === 0">
      <span v-text="$t('shopDailyReportApp.client.home.notFound')">No clients found</span>
    </div>
    <div class="table-responsive" v-if="clients && clients.length > 0">
      <table class="table table-striped" aria-describedby="clients">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('shopDailyReportApp.client.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('surName')">
              <span v-text="$t('shopDailyReportApp.client.surName')">Sur Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'surName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('debtAmount')">
              <span v-text="$t('shopDailyReportApp.client.debtAmount')">Debt Amount</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'debtAmount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('address')">
              <span v-text="$t('shopDailyReportApp.client.address')">Address</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'address'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('phone')">
              <span v-text="$t('shopDailyReportApp.client.phone')">Phone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('notes')">
              <span v-text="$t('shopDailyReportApp.client.notes')">Notes</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'notes'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdDate')">
              <span v-text="$t('shopDailyReportApp.client.createdDate')">Created Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('shopDailyReportApp.client.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="client in clients" :key="client.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ClientView', params: { clientId: client.id } }">{{ client.id }}</router-link>
            </td>
            <td>{{ client.name }}</td>
            <td>{{ client.surName }}</td>
            <td>{{ client.debtAmount }}</td>
            <td>{{ client.address }}</td>
            <td>{{ client.phone }}</td>
            <td>{{ client.notes }}</td>
            <td>{{ client.createdDate ? $d(Date.parse(client.createdDate), 'short') : '' }}</td>
            <td>{{ client.code }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ClientView', params: { clientId: client.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ClientEdit', params: { clientId: client.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(client)"
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
        ><span id="shopDailyReportApp.client.delete.question" data-cy="clientDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-client-heading" v-text="$t('shopDailyReportApp.client.delete.question', { id: removeId })">
          Are you sure you want to delete this Client?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-client"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeClient()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="clients && clients.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./client.component.ts"></script>
