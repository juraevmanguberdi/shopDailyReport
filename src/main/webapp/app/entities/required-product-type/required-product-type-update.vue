<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.requiredProductType.home.createOrEditLabel"
          data-cy="RequiredProductTypeCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.requiredProductType.home.createOrEditLabel')"
        >
          Create or edit a RequiredProductType
        </h2>
        <div>
          <div class="form-group" v-if="requiredProductType.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="requiredProductType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.requiredProductType.name')" for="required-product-type-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="required-product-type-name"
              data-cy="name"
              :class="{ valid: !$v.requiredProductType.name.$invalid, invalid: $v.requiredProductType.name.$invalid }"
              v-model="$v.requiredProductType.name.$model"
              required
            />
            <div v-if="$v.requiredProductType.name.$anyDirty && $v.requiredProductType.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.requiredProductType.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.requiredProductType.code')" for="required-product-type-code"
              >Code</label
            >
            <input
              type="text"
              class="form-control"
              name="code"
              id="required-product-type-code"
              data-cy="code"
              :class="{ valid: !$v.requiredProductType.code.$invalid, invalid: $v.requiredProductType.code.$invalid }"
              v-model="$v.requiredProductType.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.requiredProductType.notes')" for="required-product-type-notes"
              >Notes</label
            >
            <input
              type="text"
              class="form-control"
              name="notes"
              id="required-product-type-notes"
              data-cy="notes"
              :class="{ valid: !$v.requiredProductType.notes.$invalid, invalid: $v.requiredProductType.notes.$invalid }"
              v-model="$v.requiredProductType.notes.$model"
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
            :disabled="$v.requiredProductType.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./required-product-type-update.component.ts"></script>
