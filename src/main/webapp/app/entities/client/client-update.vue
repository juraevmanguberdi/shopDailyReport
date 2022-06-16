<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.client.home.createOrEditLabel"
          data-cy="ClientCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.client.home.createOrEditLabel')"
        >
          Create or edit a Client
        </h2>
        <div>
          <div class="form-group" v-if="client.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="client.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.client.name')" for="client-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="client-name"
              data-cy="name"
              :class="{ valid: !$v.client.name.$invalid, invalid: $v.client.name.$invalid }"
              v-model="$v.client.name.$model"
              required
            />
            <div v-if="$v.client.name.$anyDirty && $v.client.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.client.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.client.surName')" for="client-surName">Sur Name</label>
            <input
              type="text"
              class="form-control"
              name="surName"
              id="client-surName"
              data-cy="surName"
              :class="{ valid: !$v.client.surName.$invalid, invalid: $v.client.surName.$invalid }"
              v-model="$v.client.surName.$model"
              required
            />
            <div v-if="$v.client.surName.$anyDirty && $v.client.surName.$invalid">
              <small class="form-text text-danger" v-if="!$v.client.surName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.client.debtAmount')" for="client-debtAmount"
              >Debt Amount</label
            >
            <input
              type="number"
              class="form-control"
              name="debtAmount"
              id="client-debtAmount"
              data-cy="debtAmount"
              :class="{ valid: !$v.client.debtAmount.$invalid, invalid: $v.client.debtAmount.$invalid }"
              v-model.number="$v.client.debtAmount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.client.address')" for="client-address">Address</label>
            <input
              type="text"
              class="form-control"
              name="address"
              id="client-address"
              data-cy="address"
              :class="{ valid: !$v.client.address.$invalid, invalid: $v.client.address.$invalid }"
              v-model="$v.client.address.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.client.phone')" for="client-phone">Phone</label>
            <input
              type="text"
              class="form-control"
              name="phone"
              id="client-phone"
              data-cy="phone"
              :class="{ valid: !$v.client.phone.$invalid, invalid: $v.client.phone.$invalid }"
              v-model="$v.client.phone.$model"
              required
            />
            <div v-if="$v.client.phone.$anyDirty && $v.client.phone.$invalid">
              <small class="form-text text-danger" v-if="!$v.client.phone.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.client.notes')" for="client-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="client-notes"
              data-cy="notes"
              :class="{ valid: !$v.client.notes.$invalid, invalid: $v.client.notes.$invalid }"
              v-model="$v.client.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.client.createdDate')" for="client-createdDate"
              >Created Date</label
            >
            <div class="d-flex">
              <input
                id="client-createdDate"
                data-cy="createdDate"
                type="datetime-local"
                class="form-control"
                name="createdDate"
                :class="{ valid: !$v.client.createdDate.$invalid, invalid: $v.client.createdDate.$invalid }"
                :value="convertDateTimeFromServer($v.client.createdDate.$model)"
                @change="updateInstantField('createdDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.client.code')" for="client-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="client-code"
              data-cy="code"
              :class="{ valid: !$v.client.code.$invalid, invalid: $v.client.code.$invalid }"
              v-model="$v.client.code.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.client.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./client-update.component.ts"></script>
