<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.paymentMethod.home.createOrEditLabel"
          data-cy="PaymentMethodCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.paymentMethod.home.createOrEditLabel')"
        >
          Create or edit a PaymentMethod
        </h2>
        <div>
          <div class="form-group" v-if="paymentMethod.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="paymentMethod.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.paymentMethod.name')" for="payment-method-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="payment-method-name"
              data-cy="name"
              :class="{ valid: !$v.paymentMethod.name.$invalid, invalid: $v.paymentMethod.name.$invalid }"
              v-model="$v.paymentMethod.name.$model"
              required
            />
            <div v-if="$v.paymentMethod.name.$anyDirty && $v.paymentMethod.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.paymentMethod.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.paymentMethod.code')" for="payment-method-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="payment-method-code"
              data-cy="code"
              :class="{ valid: !$v.paymentMethod.code.$invalid, invalid: $v.paymentMethod.code.$invalid }"
              v-model="$v.paymentMethod.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.paymentMethod.note')" for="payment-method-note">Note</label>
            <input
              type="text"
              class="form-control"
              name="note"
              id="payment-method-note"
              data-cy="note"
              :class="{ valid: !$v.paymentMethod.note.$invalid, invalid: $v.paymentMethod.note.$invalid }"
              v-model="$v.paymentMethod.note.$model"
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
            :disabled="$v.paymentMethod.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./payment-method-update.component.ts"></script>
