<template>
  <div>
    <h2 id="page-heading" data-cy="RequiredProductHeading">
      <span v-text="$t('shopDailyReportApp.requiredProduct.home.title')" id="required-product-heading">Required Products</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopDailyReportApp.requiredProduct.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RequiredProductCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-required-product"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopDailyReportApp.requiredProduct.home.createLabel')"> Create a new Required Product </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && requiredProducts && requiredProducts.length === 0">
      <span v-text="$t('shopDailyReportApp.requiredProduct.home.notFound')">No requiredProducts found</span>
    </div>
    <div class="table-responsive" v-if="requiredProducts && requiredProducts.length > 0">
      <table class="table table-striped" aria-describedby="requiredProducts">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('shopDailyReportApp.requiredProduct.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('note')">
              <span v-text="$t('shopDailyReportApp.requiredProduct.note')">Note</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'note'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('shopDailyReportApp.requiredProduct.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('requiredProductType.id')">
              <span v-text="$t('shopDailyReportApp.requiredProduct.requiredProductType')">Required Product Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'requiredProductType.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="requiredProduct in requiredProducts" :key="requiredProduct.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RequiredProductView', params: { requiredProductId: requiredProduct.id } }">{{
                requiredProduct.id
              }}</router-link>
            </td>
            <td>{{ requiredProduct.name }}</td>
            <td>{{ requiredProduct.note }}</td>
            <td>{{ requiredProduct.code }}</td>
            <td>
              <div v-if="requiredProduct.requiredProductType">
                <router-link
                  :to="{ name: 'RequiredProductTypeView', params: { requiredProductTypeId: requiredProduct.requiredProductType.id } }"
                  >{{ requiredProduct.requiredProductType.id }}</router-link
                >
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RequiredProductView', params: { requiredProductId: requiredProduct.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RequiredProductEdit', params: { requiredProductId: requiredProduct.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(requiredProduct)"
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
          id="shopDailyReportApp.requiredProduct.delete.question"
          data-cy="requiredProductDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-requiredProduct-heading" v-text="$t('shopDailyReportApp.requiredProduct.delete.question', { id: removeId })">
          Are you sure you want to delete this Required Product?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-requiredProduct"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRequiredProduct()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="requiredProducts && requiredProducts.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./required-product.component.ts"></script>
